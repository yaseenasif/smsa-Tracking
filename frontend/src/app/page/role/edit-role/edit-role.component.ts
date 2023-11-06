import { Component } from '@angular/core';
import {MenuItem, MessageService} from 'primeng/api'
import { PermissionService } from '../../permission/service/permission.service';
import { Permission } from 'src/app/model/Permission';
import { RoleService } from '../service/role.service';
import { Role } from 'src/app/model/Role';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-edit-role',
  templateUrl: './edit-role.component.html',
  styleUrls: ['./edit-role.component.scss'],
  providers: [MessageService]
})
export class EditRoleComponent {
  items: MenuItem[] | undefined;

  permissions!: Permission[];
  fullPermissions!: Permission[];
  rId!: number;
  role:Role={
    id:null,
    name:null,
    permissions:null
  };

  constructor(private permissionService:PermissionService,
              private roleService:RoleService,
              private route:ActivatedRoute,
              private messageService:MessageService,
              private router:Router) { }
  name!:string;
  
  ngOnInit(): void {
    this.rId = +this.route.snapshot.paramMap.get('id')!;
    this.getRoleById();
    this.items = [{ label: 'Role list',routerLink:'/role'},{ label: 'Edit Role'}];
    // this.getAllPermissions();
  }

  getAllPermissions(){
    this.permissionService.getALLPermission().subscribe((res:Permission[])=>{  
      this.permissions=res.filter(el=>el.status && el.name!='Dash Board' && el.name!='Role');
      console.log(this.permissions);
      
      this.fullPermissions=res.filter(el=>el.status);
    },error=>{
    })
  }

  getRoleById(){
    this.roleService.geRoleByID(this.rId).subscribe((res:Role)=>{
      this.role=res; 
      this.getAllPermissions();
    },error=>{
    })
  }
  onSubmit() {
    if(this.role.name=='ROLE_ADMIN'){
      this.role.permissions?.push(this.fullPermissions!.find(el=>el.name=='Role')!)
      this.role.permissions?.push(this.fullPermissions!.find(el=>el.name=='Dash Board')!)
    }else if(this.role.name=='ROLE_USER'){
      this.role.permissions?.push(this.fullPermissions!.find(el=>el.name=='Dash Board')!)
    }
    this.roleService.assignPermissionToRole(this.role).subscribe(res=>{
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'role is updated on id'+res.id});
      setTimeout(() => {
        this.router.navigate(['/role']);
      },800);
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'role is not updated'});
    })  
  }

}

