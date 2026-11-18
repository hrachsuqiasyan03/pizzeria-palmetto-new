package am.trainings.palmetto.repository;

import am.trainings.palmetto.common.BaseRepository;
import am.trainings.palmetto.customer.Order;

public interface OrderRepository extends BaseRepository {

    long save(Order order);
}