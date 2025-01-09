import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdmniTemplateComponent } from './admni-template.component';

describe('AdmniTemplateComponent', () => {
  let component: AdmniTemplateComponent;
  let fixture: ComponentFixture<AdmniTemplateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AdmniTemplateComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdmniTemplateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
