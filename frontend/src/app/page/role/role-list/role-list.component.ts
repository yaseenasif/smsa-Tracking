import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { RoleService } from '../service/role.service';
import { Role } from 'src/app/model/Role';

@Component({
  selector: 'app-role-list',
  templateUrl: './role-list.component.html',
  styleUrls: ['./role-list.component.scss']
})
export class RoleListComponent {
  constructor(private roleService:RoleService) { }
  roles!:Role[];
  items: MenuItem[] | undefined;

 

  ngOnInit() {
      this.items = [{ label: 'Role List'}];
      this.getAllLRole();
  }

  getAllLRole(){
    this.roleService.getALLRole().subscribe((res:Role[])=>{
      this.roles=res;
      console.log(this.roles);      
    },error=>{  
    })
   }
}
