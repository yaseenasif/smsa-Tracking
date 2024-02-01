import { Component } from '@angular/core';
import { UserService } from '../service/user.service';
import { MenuItem, MessageService } from 'primeng/api';
import { User } from 'src/app/model/User';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-archive-user',
  templateUrl: './archive-user.component.html',
  styleUrls: ['./archive-user.component.scss'],
  providers:[MessageService]
})
export class ArchiveUserComponent {

  items: MenuItem[] | undefined;
  users!:User[]
  
  constructor(private router: Router,private userService:UserService,private messageService:MessageService) { }
  ngOnInit() {
    this.items = [{ label: 'User List', routerLink: '/user'},{ label: 'Archive User'}];
    this.getInActiveUser();
  }

 getInActiveUser(){
  this.userService.getInActiveUser().subscribe((res:User[])=>{
    this.users=res;  
  },error=>{
    this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
  })
 }

 unArchiveUser(user:User) {
  user.status=true
  this.userService.updateUserById(user.id!,user).subscribe(res=>{
    this.messageService.add({ severity: 'success', summary: 'Success', detail: 'User is Un Archive successfully' });
    setTimeout(() => {
      this.router.navigate(['/user']);
    },800);
  },error=>{
    this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
  })
 }


}
