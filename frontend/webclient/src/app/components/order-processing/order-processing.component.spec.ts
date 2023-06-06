import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderProcessingComponent } from './order-processing.component';

describe('OrderProcessingComponent', () => {
  let component: OrderProcessingComponent;
  let fixture: ComponentFixture<OrderProcessingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrderProcessingComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OrderProcessingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
