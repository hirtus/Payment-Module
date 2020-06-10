package school.attractor.payment_module.domain.order;import com.querydsl.core.types.dsl.StringPath;import org.springframework.data.jpa.repository.JpaRepository;import org.springframework.data.querydsl.QuerydslPredicateExecutor;import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;import org.springframework.data.querydsl.binding.QuerydslBindings;import org.springframework.stereotype.Repository;import school.attractor.payment_module.domain.shop.Shop;import java.util.ArrayList;import java.util.Date;import java.util.List;import java.util.Optional;@Repositorypublic interface OrderRepository extends JpaRepository<Order, Integer>,        QuerydslPredicateExecutor<Order>,        QuerydslBinderCustomizer<QOrder> {    @Override    default void customize(QuerydslBindings bindings, QOrder order) {        bindings.bind ( order.orderId )                .first ( (path, value) -> path.eq ( value.intValue() ) );        bindings.bind ( order.shop.siteName )                .first ( (path, value) -> path.contains ( value ) );        bindings.bind ( String.class )                .first ( (StringPath path, String value) -> path.containsIgnoreCase ( value ) );        bindings.bind ( order.amount )                .all ( (path, value) -> {                    List<? extends Integer> amounts = new ArrayList<> ( value );                    if (amounts.get ( 0 ) == null && amounts.get ( 1 ) == null) {                        return Optional.empty ( );                    } else {                        if (amounts.get ( 1 ) == null) {                            return Optional.of ( path.goe ( amounts.get ( 0 ) ) );                        } else if (amounts.get ( 0 ) == null ) {                            return Optional.of ( path.loe ( amounts.get ( 1 ) ) );                        }else{                            return Optional.of ( path.between ( amounts.get ( 0 ), amounts.get ( 1 ) ) );                        }                    }                } );        bindings.bind(order.date)                .all((path, value) -> {                    List<? extends Date> dates = new ArrayList<>(value);                   if (dates.get ( 0 ) == null && dates.get ( 1 ) == null) {                        return Optional.empty ( );                   } else if (dates.get ( 1 ) == null) {                       return Optional.of ( path.goe ( dates.get ( 0 ) ) );                   } else if (dates.get ( 0 ) == null ) {                       return Optional.of ( path.loe ( dates.get ( 1 ) ) );                   }else{                       return Optional.of ( path.between ( dates.get ( 0 ), dates.get ( 1 ) ) );                   }                });    }    List<Order> findAllByShopIn(List<Shop> shops);    Optional<Order> findByOrderId(Integer id);}