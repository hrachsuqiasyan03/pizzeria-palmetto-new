package am.trainings.palmetto.common;

public abstract class AbstractRepositoryFactory {
    public abstract BaseRepository
    getRepositoryByType(RepositoryTypes type);
}
