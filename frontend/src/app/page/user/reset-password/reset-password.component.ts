import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { MenuItem, MessageService } from 'primeng/api';
import { RoleService } from '../../role/service/role.service';
import { LocationService } from '../../location/service/location.service';
import { UserService } from '../service/user.service';
import { User } from 'src/app/model/User';
import { Role } from 'src/app/model/Role';
import { ResetPassword } from 'src/app/model/ResetPassword';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss'],
  providers:[MessageService]
})
export class ResetPasswordComponent {

  items: MenuItem[] | undefined;
  checked: boolean = false;
  

  constructor (private router: Router,private messageService:MessageService,private userService:UserService) { }


  resetPassword:ResetPassword={
    id: undefined,
    oldPassword: undefined,
    newPassword: undefined
  };


  ngOnInit(): void {
    this.items = [{ label: 'Reset Password'}];
  }

  
 

  onSubmit(){ 
    this.userService.resetPassword(this.resetPassword).subscribe(res=>{
      debugger
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Password reset successfully' });
      setTimeout(() => {
        this.router.navigate(['/home']);
      },800);
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })  
  }
   

}
