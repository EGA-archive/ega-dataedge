package eu.elixir.ega.ebi.dataedge.service.ena.htsget.service;

import eu.elixir.ega.ebi.dataedge.dto.ena.dto.LinkToSequence;

public interface SequenceLinkService {
    public LinkToSequence getLinkToFile(String accession);
}
