import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { Permission } from 'src/app/model/Permission';
import { PermissionService } from '../service/permission.service';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-permission-list',
  templateUrl: './permission-list.component.html',
  styleUrls: ['./permission-list.component.scss'],
  providers: [MessageService]
})
export class PermissionListComponent {
  constructor(private permissionService:PermissionService,private messageService:MessageService) { }
  permissions!:Permission[];
  items: MenuItem[] | undefined;
  visible: boolean = false;
  pID!:number

  ngOnInit() {
      this.items = [{ label: 'Permission'}];
      this.getAllPermissions();
  }
   getAllPermissions(){
    this.permissionService.getALLPermission().subscribe((res:Permission[])=>{
      this.permissions=res.filter(el=>el.status);   
    },error=>{
    })
   }
   deletePermissionByID(id:number){
    this.permissionService.deletePermissionByID(id).subscribe((res:Permission)=>{
      this.visible = false;
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Permission is deleted on id '+res!.id!.toString()});
      this.getAllPermissions();
    },error=>{
      
    });
   }

  showDialog(id:number) {
    this.pID=id;
    this.visible = true;
  }
}
