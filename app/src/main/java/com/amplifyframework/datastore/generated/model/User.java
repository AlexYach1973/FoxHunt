package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the UserModel type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Users", type = Model.Type.USER, version = 1, authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class User implements Model {
  public static final QueryField ID = field("UserModel", "id");
  public static final QueryField NAME = field("UserModel", "name");
  public static final QueryField NUMBER_OF_GAME = field("UserModel", "numberOfGame");
  public static final QueryField MIN_NUMBER_OF_MOVES = field("UserModel", "minNumberOfMoves");
  public static final QueryField MAX_NUMBER_OF_MOVES = field("UserModel", "maxNumberOfMoves");
  public static final QueryField SUM_NUMBER_OF_MOVES = field("UserModel", "sumNumberOfMoves");
  public static final QueryField MEAN_NUMBER_OF_MOVES = field("UserModel", "meanNumberOfMoves");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String name;
  private final @ModelField(targetType="Int") Integer numberOfGame;
  private final @ModelField(targetType="Int") Integer minNumberOfMoves;
  private final @ModelField(targetType="Int") Integer maxNumberOfMoves;
  private final @ModelField(targetType="Int") Integer sumNumberOfMoves;
  private final @ModelField(targetType="Float") Double meanNumberOfMoves;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String resolveIdentifier() {
    return id;
  }
  
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public Integer getNumberOfGame() {
      return numberOfGame;
  }
  
  public Integer getMinNumberOfMoves() {
      return minNumberOfMoves;
  }
  
  public Integer getMaxNumberOfMoves() {
      return maxNumberOfMoves;
  }
  
  public Integer getSumNumberOfMoves() {
      return sumNumberOfMoves;
  }
  
  public Double getMeanNumberOfMoves() {
      return meanNumberOfMoves;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private User(String id, String name, Integer numberOfGame, Integer minNumberOfMoves, Integer maxNumberOfMoves, Integer sumNumberOfMoves, Double meanNumberOfMoves) {
    this.id = id;
    this.name = name;
    this.numberOfGame = numberOfGame;
    this.minNumberOfMoves = minNumberOfMoves;
    this.maxNumberOfMoves = maxNumberOfMoves;
    this.sumNumberOfMoves = sumNumberOfMoves;
    this.meanNumberOfMoves = meanNumberOfMoves;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      User user = (User) obj;
      return ObjectsCompat.equals(getId(), user.getId()) &&
              ObjectsCompat.equals(getName(), user.getName()) &&
              ObjectsCompat.equals(getNumberOfGame(), user.getNumberOfGame()) &&
              ObjectsCompat.equals(getMinNumberOfMoves(), user.getMinNumberOfMoves()) &&
              ObjectsCompat.equals(getMaxNumberOfMoves(), user.getMaxNumberOfMoves()) &&
              ObjectsCompat.equals(getSumNumberOfMoves(), user.getSumNumberOfMoves()) &&
              ObjectsCompat.equals(getMeanNumberOfMoves(), user.getMeanNumberOfMoves()) &&
              ObjectsCompat.equals(getCreatedAt(), user.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), user.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getNumberOfGame())
      .append(getMinNumberOfMoves())
      .append(getMaxNumberOfMoves())
      .append(getSumNumberOfMoves())
      .append(getMeanNumberOfMoves())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("UserModel {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("numberOfGame=" + String.valueOf(getNumberOfGame()) + ", ")
      .append("minNumberOfMoves=" + String.valueOf(getMinNumberOfMoves()) + ", ")
      .append("maxNumberOfMoves=" + String.valueOf(getMaxNumberOfMoves()) + ", ")
      .append("sumNumberOfMoves=" + String.valueOf(getSumNumberOfMoves()) + ", ")
      .append("meanNumberOfMoves=" + String.valueOf(getMeanNumberOfMoves()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
      return new Builder();
  }
  
  /**
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static User justId(String id) {
    return new User(
      id,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      numberOfGame,
      minNumberOfMoves,
      maxNumberOfMoves,
      sumNumberOfMoves,
      meanNumberOfMoves);
  }
  public interface BuildStep {
    User build();
    BuildStep id(String id);
    BuildStep name(String name);
    BuildStep numberOfGame(Integer numberOfGame);
    BuildStep minNumberOfMoves(Integer minNumberOfMoves);
    BuildStep maxNumberOfMoves(Integer maxNumberOfMoves);
    BuildStep sumNumberOfMoves(Integer sumNumberOfMoves);
    BuildStep meanNumberOfMoves(Double meanNumberOfMoves);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String name;
    private Integer numberOfGame;
    private Integer minNumberOfMoves;
    private Integer maxNumberOfMoves;
    private Integer sumNumberOfMoves;
    private Double meanNumberOfMoves;
    @Override
     public User build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new User(
          id,
          name,
          numberOfGame,
          minNumberOfMoves,
          maxNumberOfMoves,
          sumNumberOfMoves,
          meanNumberOfMoves);
    }
    
    @Override
     public BuildStep name(String name) {
        this.name = name;
        return this;
    }
    
    @Override
     public BuildStep numberOfGame(Integer numberOfGame) {
        this.numberOfGame = numberOfGame;
        return this;
    }
    
    @Override
     public BuildStep minNumberOfMoves(Integer minNumberOfMoves) {
        this.minNumberOfMoves = minNumberOfMoves;
        return this;
    }
    
    @Override
     public BuildStep maxNumberOfMoves(Integer maxNumberOfMoves) {
        this.maxNumberOfMoves = maxNumberOfMoves;
        return this;
    }
    
    @Override
     public BuildStep sumNumberOfMoves(Integer sumNumberOfMoves) {
        this.sumNumberOfMoves = sumNumberOfMoves;
        return this;
    }
    
    @Override
     public BuildStep meanNumberOfMoves(Double meanNumberOfMoves) {
        this.meanNumberOfMoves = meanNumberOfMoves;
        return this;
    }
    
    /**
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String name, Integer numberOfGame, Integer minNumberOfMoves, Integer maxNumberOfMoves, Integer sumNumberOfMoves, Double meanNumberOfMoves) {
      super.id(id);
      super.name(name)
        .numberOfGame(numberOfGame)
        .minNumberOfMoves(minNumberOfMoves)
        .maxNumberOfMoves(maxNumberOfMoves)
        .sumNumberOfMoves(sumNumberOfMoves)
        .meanNumberOfMoves(meanNumberOfMoves);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder numberOfGame(Integer numberOfGame) {
      return (CopyOfBuilder) super.numberOfGame(numberOfGame);
    }
    
    @Override
     public CopyOfBuilder minNumberOfMoves(Integer minNumberOfMoves) {
      return (CopyOfBuilder) super.minNumberOfMoves(minNumberOfMoves);
    }
    
    @Override
     public CopyOfBuilder maxNumberOfMoves(Integer maxNumberOfMoves) {
      return (CopyOfBuilder) super.maxNumberOfMoves(maxNumberOfMoves);
    }
    
    @Override
     public CopyOfBuilder sumNumberOfMoves(Integer sumNumberOfMoves) {
      return (CopyOfBuilder) super.sumNumberOfMoves(sumNumberOfMoves);
    }
    
    @Override
     public CopyOfBuilder meanNumberOfMoves(Double meanNumberOfMoves) {
      return (CopyOfBuilder) super.meanNumberOfMoves(meanNumberOfMoves);
    }
  }
  
}
