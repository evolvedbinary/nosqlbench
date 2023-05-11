package io.nosqlbench.adapter.pinecone.opdispensers;

import io.nosqlbench.adapter.pinecone.PineconeDriverAdapter;
import io.nosqlbench.adapter.pinecone.PineconeSpace;
import io.nosqlbench.adapter.pinecone.ops.PineconeDeleteOp;
import io.nosqlbench.adapter.pinecone.ops.PineconeOp;
import io.nosqlbench.engine.api.templating.ParsedOp;
import io.pinecone.PineconeConnection;
import io.pinecone.proto.DeleteRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.LongFunction;

/**
 * return DeleteRequest.newBuilder()
 *             .setNamespace(namespace)
 *             .addAllIds(Arrays.asList(idsToDelete))
 *             .setDeleteAll(false)
 *             .build();
 */

public class PineconeDeleteOpDispenser extends PineconeOpDispenser {
    private final LongFunction<DeleteRequest> deleteRequestFunc;

    public PineconeDeleteOpDispenser(PineconeDriverAdapter adapter,
                                     ParsedOp op,
                                     LongFunction<PineconeSpace> pcFunction,
                                     LongFunction<String> targetFunction) {
        super(adapter, op, pcFunction, targetFunction);

        indexNameFunc = op.getAsRequiredFunction("delete", String.class);
        deleteRequestFunc = createDeleteRequestFunction(op);
    }

    @Override
    public PineconeOp apply(long value) {
        return new PineconeDeleteOp(pcFunction.apply(value).getConnection(indexNameFunc.apply(value)),
            deleteRequestFunc.apply(value));
    }

    private LongFunction<DeleteRequest> createDeleteRequestFunction(ParsedOp op) {
        LongFunction<DeleteRequest.Builder> rFunc = l -> DeleteRequest.newBuilder();

        Optional<LongFunction<String>> nFunc = op.getAsOptionalFunction("namespace", String.class);
        if (nFunc.isPresent()) {
            LongFunction<DeleteRequest.Builder> finalFunc = rFunc;
            LongFunction<String> af = nFunc.get();
            rFunc = l -> finalFunc.apply(l).setNamespace(af.apply(l));
        }

        Optional<LongFunction<String>> iFunc = op.getAsOptionalFunction("ids", String.class);
        if (iFunc.isPresent()) {
            LongFunction<DeleteRequest.Builder> finalFunc = rFunc;
            LongFunction<String> af = iFunc.get();
            LongFunction<List<String>> alf = l -> {
                String[] vals = af.apply(l).split(",");
                return Arrays.asList(vals);
            };
            rFunc = l -> finalFunc.apply(l).addAllIds(alf.apply(l));
        }

        Optional<LongFunction<Boolean>> aFunc = op.getAsOptionalFunction("deleteall", Boolean.class);
        if (aFunc.isPresent()) {
            LongFunction<DeleteRequest.Builder> finalFunc = rFunc;
            LongFunction<Boolean> af = aFunc.get();
            rFunc = l -> finalFunc.apply(l).setDeleteAll(af.apply(l));
        }

        LongFunction<DeleteRequest.Builder> finalRFunc = rFunc;
        return l -> finalRFunc.apply(l).build();
    }


}
