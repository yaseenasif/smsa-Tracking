import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {MenuItem} from 'primeng/api'
import { PermissionService } from '../service/permission.service';
import { Permission } from 'src/app/model/Permission';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-edit-permission',
  templateUrl: './edit-permission.component.html',
  styleUrls: ['./edit-permission.component.scss'],
  providers: [MessageService]
})
export class EditPermissionComponent {
  items: MenuItem[] | undefined;
  pId!: number;
  name!:string;
  permission:Permission={
    id: null,
    name: null,
    status: null
  };

  constructor(private route: ActivatedRoute,
              private permissionService:PermissionService,
              private messageService: MessageService,
              private router: Router) { }

  ngOnInit(): void {
    this.pId = +this.route.snapshot.paramMap.get('id')!;
    this.items = [{ label: 'Permission list',routerLink:'/permission'},{ label: 'Edit Permission'}];
    this.getPermissionById();
  }

  getPermissionById(){
   this.permissionService.getByIDPermission(this.pId).subscribe((res:Permission)=>{
    this.permission.name=res.name;
   },error=>{
    this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Can not permission by id'});
   })
  }
  
  onSubmit() {
    this.permissionService.updatePermissionById(this.pId,this.permission).subscribe(res=>{
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Permission is updated on id'+res.id});
      setTimeout(() => {
        this.router.navigate(['/permission']);
      },800);
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Permission is not updated'});
    })  
  }

}
