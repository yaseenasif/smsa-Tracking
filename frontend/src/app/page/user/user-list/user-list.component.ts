import { Component, OnInit } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { User } from 'src/app/model/User';
import { UserService } from '../service/user.service';
import { AuthguardService } from 'src/app/auth-service/authguard/authguard.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss'],
  providers:[MessageService]
})
export class UserListComponent implements OnInit {

  items: MenuItem[] | undefined;
  users!:User[]

  visible: boolean = false;
  uID!:number

  constructor(private userService:UserService,private messageService:MessageService,private authguardService:AuthguardService) { }
  
  ngOnInit() {
      this.items = [{ label: 'User List'}];
      this.getAllUser();
  }

  hasPermission(permission:string):boolean{
    return this.authguardService.hasPermission(permission)
  }

  getAllUser(){
    this.userService.getAllUser().subscribe((res:User[])=>{
      this.users=res;  
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
   }

   deleteUserByID(id:number){
    this.userService.deleteUserByID(id).subscribe((res:User)=>{
      this.visible = false;
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'User is deleted on id '+res!.id!.toString()});
      this.getAllUser();
    },error=>{
      this.visible = false;
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    });
   }

  showDialog(id:number) {
    this.uID=id;
    this.visible = true;
  }
  
}
