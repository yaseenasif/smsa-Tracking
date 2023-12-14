import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { RoleService } from '../service/role.service';
import { Role } from 'src/app/model/Role';

@Component({
  selector: 'app-role-list',
  templateUrl: './role-list.component.html',
  styleUrls: ['./role-list.component.scss'],
  providers:[MessageService]
})
export class RoleListComponent {
  constructor(private roleService:RoleService,private messageService:MessageService) { }
  roles!:Role[];
  items: MenuItem[] | undefined;

 

  ngOnInit() {
      this.items = [{ label: 'Role List'}];
      this.getAllLRole();
  }

  getAllLRole(){
    this.roleService.getALLRole().subscribe((res:Role[])=>{
      this.roles=res;
    
    },error=>{  
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
   }
}
