import { Component, ViewChild } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { Permission } from 'src/app/model/Permission';
import { PermissionService } from '../service/permission.service';
import { MessageService } from 'primeng/api';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-permission',
  templateUrl: './add-permission.component.html',
  styleUrls: ['./add-permission.component.scss'],
  providers: [MessageService]
})
export class AddPermissionComponent {
  items: MenuItem[] | undefined;
  permission:Permission={
    id: null,
    name: null,
    status: null
  };

  constructor(private permissionService:PermissionService,
              private messageService: MessageService,
              private router: Router) { }
 
  ngOnInit(): void {
    this.items = [{ label: 'Permission list',routerLink:'/permission'},{ label: 'Add Permission'}];
  }
  
  onSubmit() {
    this.permissionService.addPermission(this.permission).subscribe(res=>{
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Permission is added' });
      setTimeout(() => {
        this.router.navigate(['/permission']);
      },800);
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Permission is not added' });
    })  
  }
}
