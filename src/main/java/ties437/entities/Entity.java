package ties437.entities;

import org.apache.jena.rdf.model.Literal;

/**
 * Created by chinhnk on 4/3/2017.
 */
public class Entity {
    protected String uri;
//    private List<Literal>

    public Entity(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entity)) return false;

        Entity entity = (Entity) o;

        return getUri().equals(entity.getUri());

    }

    @Override
    public int hashCode() {
        return getUri().hashCode();
    }
}
