package com.wodder.product.application;

import com.wodder.product.domain.model.product.ProductCreated;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;
import javax.enterprise.event.Event;
import javax.enterprise.event.NotificationOptions;
import javax.enterprise.util.TypeLiteral;

public class FakeProductEvent implements Event<ProductCreated> {

  List<ProductCreated> events = new ArrayList<>();

  @Override
  public void fire(ProductCreated productCreated) {
    events.add(productCreated);
  }

  @Override
  public <U extends ProductCreated> CompletionStage<U> fireAsync(U u) {
    return null;
  }

  @Override
  public <U extends ProductCreated> CompletionStage<U> fireAsync(U u,
      NotificationOptions notificationOptions) {
    return null;
  }

  @Override
  public Event<ProductCreated> select(Annotation... annotations) {
    return null;
  }

  @Override
  public <U extends ProductCreated> Event<U> select(Class<U> aClass, Annotation... annotations) {
    return null;
  }

  @Override
  public <U extends ProductCreated> Event<U> select(TypeLiteral<U> typeLiteral,
      Annotation... annotations) {
    return null;
  }
}
