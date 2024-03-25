import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { Role } from 'src/app/model/Role';
import { RoleService } from '../service/role.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-role',
  templateUrl: './add-role.component.html',
  styleUrls: ['./add-role.component.scss'],
  providers:[MessageService]
})
export class AddRoleComponent {
  items: MenuItem[] | undefined;
  role:Role={
    id:null,
    name:null,
    permissions:null
  };

  

  constructor(private roleService:RoleService,private messageService:MessageService, private router: Router) { }

  
  
  ngOnInit(): void {
    this.items = [{ label: 'Role list',routerLink:'/role'},{ label: 'Add Role'}];


  }
  onSubmit(){
    this.roleService.addRole(this.role).subscribe((res:any)=>{
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'role is updated on id'+res.id});
      setTimeout(() => {
        this.router.navigate(['/role']);
      },800);
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })  
  }
}

