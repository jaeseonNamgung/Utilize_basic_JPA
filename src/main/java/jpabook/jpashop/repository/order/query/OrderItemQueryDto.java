package jpabook.jpashop.repository.order.query;

public record OrderItemQueryDto(
        Long orderId,
        String itemName,
        int OrderPrice,
        int count
) {
    public static OrderItemQueryDto of(
            Long orderId,
            String itemName,
            int OrderPrice,
            int count
    ){
        return new OrderItemQueryDto(orderId, itemName, OrderPrice, count);
    }
}
