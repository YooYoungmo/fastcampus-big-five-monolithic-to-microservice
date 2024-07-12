package fastcampus.bigfive.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "commentaries")
public class Commentary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @Column(columnDefinition = "TEXT")
    private String comments;

    @OneToOne(mappedBy = "commentary")
    private Evaluation evaluation;

    public Commentary(String comments) {
        this.comments = comments;
    }

    public String getComments() {
        return comments;
    }
}
