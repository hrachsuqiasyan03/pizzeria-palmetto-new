package am.trainings.palmetto.common;

public abstract class AbstractRepositoryFactory {
    //նախատեսված է տարբեր տեսակի Repository-ներ ստանալու համար
    public abstract BaseRepository
    getRepositoryByType(RepositoryTypes type);
}
