import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DsvTableComponent } from './dsv-table.component';

describe('DsvTableComponent', () => {
  let component: DsvTableComponent;
  let fixture: ComponentFixture<DsvTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DsvTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DsvTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
