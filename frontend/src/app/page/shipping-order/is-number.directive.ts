import { Directive } from '@angular/core';
import { NG_VALIDATORS, AbstractControl, ValidationErrors } from '@angular/forms';

@Directive({
  selector: '[appIsNumber]',
  providers: [{provide: NG_VALIDATORS, useExisting: IsNumberDirective, multi: true}]
})
export class IsNumberDirective {

  validate(control: AbstractControl): ValidationErrors | null {
    const value = control.value;
   
    for (let i = 0; i < value.length; i++) {
      const num = parseFloat(value[i]);
      if (!(!isNaN(num) && value[i].trim() !== '')) {
        return { 'invalidValue': true };
      }
    }
    return null;
}

}
