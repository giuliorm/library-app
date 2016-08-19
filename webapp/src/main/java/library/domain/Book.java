package library.domain;

import javax.persistence.*;

@Entity
@SuppressWarnings("JpaQlInspection")
@NamedQueries({
    @NamedQuery(query="FROM Book", name="Library.findAll"),
    @NamedQuery(query = "FROM Book WHERE id = :id", name="Library.findById"),
    @NamedQuery(query = "DELETE Book WHERE id = :id", name="Library.remove"),
    @NamedQuery(query = "FROM Book B WHERE B.bookName = :bookname", name="Library.findByName"),
    @NamedQuery(query = "FROM Book B WHERE B.bookName = :bookname AND B.author = :author",
            name="Library.findByNameAndAuthor")
})
@Table(name = "library")
public class Book  {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int bookId;

    @Column(name = "bookname")
    private String bookName;

    @Column(name = "author")
    private String author;

    public static final String TABLENAME = "library";
    public static final String ID_COLUMN = "id";
    public static final String BOOKNAME_COLUMN = "bookname";
    public static final String AUTHOR_COLUMN = "author";

    public static final String ALL = "Library.findAll";
    public static final String REMOVE = "Library.remove";
    public static final String FINDBYID = "Library.findById";
    public static final String FINDBYNAME = "Library.findByName";
    public static final String FINDBYNAMEANDAUTHOR = "Library.findByNameAndAuthor";
    public static final String INSERT = "insert into " + TABLENAME + "(" + BOOKNAME_COLUMN + ", "
            + AUTHOR_COLUMN + ") values(?1,?2)";
    public static final String UPDATE = "update " + TABLENAME + " set " + BOOKNAME_COLUMN +
            "=?1, " + AUTHOR_COLUMN + "=?2 where " + ID_COLUMN + "=?3";

    public int getId () {
        return bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String name) {
        this.bookName = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
