import { Directive } from '@angular/core';
import { NG_VALIDATORS, AbstractControl, ValidationErrors } from '@angular/forms';

@Directive({
  selector: '[appTwelveDigitValidator]',
  providers: [{provide: NG_VALIDATORS, useExisting: TwelveDigitValidatorDirective, multi: true}]
})
export class TwelveDigitValidatorDirective {

  validate(control: AbstractControl): ValidationErrors | null {
    const value = control.value;
   
    for (let i = 0; i < value.length; i++) {
      if (value[i].length !== 12) {
        return { 'invalidLength12': true };
      }
    }
    return null;
}
}
