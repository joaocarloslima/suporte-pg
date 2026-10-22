package br.com.fiap.suporte.service;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.reader.pdf.ParagraphPdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.stereotype.Service;

@Service
public class InjestionService  {

    private final PgVectorStore vectorStore;

    public InjestionService(PgVectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    //@PostConstruct
    public void loadDataToDatabase(){

        var pdf = "classpath:docs/challenge.pdf";
        var document = new ParagraphPdfDocumentReader(pdf);
        var splitter = TokenTextSplitter.builder().build();
        var chunks = splitter.apply(document.get());
        vectorStore.accept(chunks);

    }

}
