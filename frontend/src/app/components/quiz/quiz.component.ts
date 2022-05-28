import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Title } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { PreferencesReq } from 'src/app/entities/preferences-req';
import { MainService } from 'src/app/services/main.service';

@Component({
  selector: 'app-quiz',
  templateUrl: './quiz.component.html',
  styleUrls: ['./quiz.component.scss']
})
export class QuizComponent implements OnInit {
  form: FormGroup;
  formSubmitted : Boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private titleSerivce: Title,
    private mainService: MainService,
    private router: Router
  ) {
    this.titleSerivce.setTitle("Hair Quiz | ShampooMe");
    this.form = this.formBuilder.group({
      ageOver25: ['',[Validators.required]],
      hairType: ['',[Validators.required]],
      scalp: ['',[Validators.required]],
      splitEnds: ['',[Validators.required]],
      dandruff: ['',[Validators.required]],
      hairLossMedium: ['',[Validators.required]],
      hairLossStrong: ['',[Validators.required]],
      thinHair: ['',[Validators.required]],
      fragrance: ['',[Validators.required]],
      bottleSize: ['',[Validators.required]],
      name: ['',[Validators.required,Validators.minLength(1)]],
    });
  }

  ngOnInit(): void {
  }

  get ageOver25():  AbstractControl | null { return this.form.get('ageOver25'); }
  get hairType():  AbstractControl | null { return this.form.get('hairType'); }
  get scalp():  AbstractControl | null { return this.form.get('scalp'); }
  get splitEnds():  AbstractControl | null { return this.form.get('splitEnds'); }
  get dandruff():  AbstractControl | null { return this.form.get('dandruff'); }
  get hairLossMedium():  AbstractControl | null { return this.form.get('hairLossMedium'); }
  get hairLossStrong():  AbstractControl | null { return this.form.get('hairLossStrong'); }
  get thinHair():  AbstractControl | null { return this.form.get('thinHair'); }
  get fragrance(): AbstractControl | null { return this.form.get('fragrance'); }
  get bottleSize(): AbstractControl | null { return this.form.get('bottleSize'); }
  get name():  AbstractControl | null { return this.form.get('name'); }

  submit() {
    this.formSubmitted = true;
    if(this.form.invalid) {
      return;
    }
    
    const preferenceReq = new PreferencesReq(
      this.ageOver25?.value,
      this.hairType?.value,
      this.scalp?.value,
      this.splitEnds?.value,
      this.dandruff?.value,
      this.hairLossMedium?.value,
      this.hairLossStrong?.value,
      this.thinHair?.value,
      this.fragrance?.value,
      this.bottleSize?.value,
      this.name?.value
    )

    this.mainService.sendPreferences(preferenceReq).subscribe(processIdRes => {
      const processId = processIdRes.processId;
      this.router.navigate([`../order/${processId}`]);
    });
  }

}
