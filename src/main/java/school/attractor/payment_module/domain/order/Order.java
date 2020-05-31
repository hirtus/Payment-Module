package school.attractor.payment_module.domain.order;import com.fasterxml.jackson.annotation.JsonBackReference;import lombok.AllArgsConstructor;import lombok.Builder;import lombok.Data;import lombok.NoArgsConstructor;import school.attractor.payment_module.domain.shop.Shop;import school.attractor.payment_module.domain.transaction.NewOrderDetails;import school.attractor.payment_module.domain.transaction.Transaction;import school.attractor.payment_module.domain.transaction.TransactionStatus;import school.attractor.payment_module.domain.transaction.TransactionType;import javax.persistence.*;import java.util.ArrayList;import java.util.Date;import java.util.List;import java.util.stream.Collectors;@Entity@Data@AllArgsConstructor@NoArgsConstructor@Builder@Table(name = "orders")public class Order {    @Id    @GeneratedValue(strategy = GenerationType.IDENTITY)    private Integer id;    @ManyToOne    @JoinColumn(name = "shop_id")    private Shop shop;    @Column    private String shopName;    @Column    private String userName;    @Column    private String email;    @Column    private String phone;    @Column    private Date date;    @Column(length = 50)    private String cardHolderName;    @Column(length = 30)    private long card;    @Column(length = 10)    private int exp;    @Column(length = 10)    private int exp_year;    @Column(length = 10)    private Integer cvc2;    @Column    private Integer amount;    @Column    private int residual;    @Column    private TransactionStatus status;    @Column    private TransactionType type;    @Column    private Integer orderId;    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")    @JsonBackReference    private List<Transaction> transactions = new ArrayList<> (  );    @Column    String RetrievalReferenceNumber;    @Column    String internalReferenceNumber;    public static Order from(OrderDTO orderDTO) {        return  Order.builder()                .orderId(orderDTO.getOrderId ())                .shopName(orderDTO.getShopName())                .userName(orderDTO.getUserName())                .cardHolderName(orderDTO.getCardHolderName())                .card(orderDTO.getCard ())                .exp(orderDTO.getExp())                .exp_year(orderDTO.getExp_year())                .cvc2(orderDTO.getCvc2())                .residual(orderDTO.getResidual())                .amount(orderDTO.getAmount())                .email(orderDTO.getEmail())                .status(orderDTO.getStatus())                .date(orderDTO.getDate())                .transactions(orderDTO.getTransactions().stream().map(Transaction::from).collect(Collectors.toList()))                .build();    }    public static Order from (NewOrderDetails orderDetails) {        return Order.builder()                .orderId(orderDetails.getOrderId())                .shopName(orderDetails.getShopName())                .type(orderDetails.getType())                .amount(orderDetails.getAmount())                .email(orderDetails.getEmail())                .userName(orderDetails.getUserName())                .phone(orderDetails.getPhone())                .card(orderDetails.getCard())                .cardHolderName(orderDetails.getCardHolderName())                .cvc2(orderDetails.getCvc2())                .exp(orderDetails.getExp())                .exp_year(orderDetails.getExp_year())                .status(TransactionStatus.NEW)                .transactions( new ArrayList<>())                .build();    }}