import { Directive } from '@angular/core';
import { AbstractControl, NG_VALIDATORS, ValidationErrors } from '@angular/forms';

@Directive({
  selector: '[appSevenDigitValidator]',
  providers: [{provide: NG_VALIDATORS, useExisting: SevenDigitValidatorDirective , multi: true}]
})
export class SevenDigitValidatorDirective {

  validate(control: AbstractControl): ValidationErrors | null {
  
    const value = control.value;
   
    for (let i = 0; i < value.length; i++) {
      if (value[i].length !== 7) {
        return { 'invalidLength': true };
      }
    }
    return null;
}
}
