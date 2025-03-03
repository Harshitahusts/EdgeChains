package com.edgechain.app.chains.retrieval;

import com.edgechain.lib.context.domain.HistoryContext;
import com.edgechain.lib.context.services.HistoryContextService;
import com.edgechain.lib.resource.ResourceHandler;
import com.edgechain.lib.rxjava.response.ChainResponse;
import java.util.List;
import reactor.core.publisher.Mono;

public abstract class RetrievalChain {

  public abstract void upsert(String input);

  public abstract Mono<List<ChainResponse>> query(String queryText, int topK);

  public abstract Mono<ChainResponse> query(
      String contextId, HistoryContextService contextService, String queryText);

  public Mono<ChainResponse> query(
      String contextId, HistoryContextService contextService, ResourceHandler resourceHandler) {
    HistoryContext context = contextService.get(contextId).getWithRetry();
    resourceHandler.upload(context.getResponse());
    return Mono.just(
        new ChainResponse("File is successfully uploaded to the provided destination"));
  }
}
