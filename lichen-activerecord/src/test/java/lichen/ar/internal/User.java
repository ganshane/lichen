package lichen.ar.internal;

import javax.persistence.*;

/**
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 */
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_sequence")
    @Column(name = "id")
    private Long id;
}
