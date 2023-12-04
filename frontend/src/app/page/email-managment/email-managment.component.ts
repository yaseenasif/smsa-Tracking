import { Component } from '@angular/core';
import { RoleService } from '../role/service/role.service';
import { Role } from 'src/app/model/Role';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-email-managment',
  templateUrl: './email-managment.component.html',
  styleUrls: ['./email-managment.component.scss']
})
export class EmailManagmentComponent {
  constructor(private roleService:RoleService) { }
  roles=[1];
  items: MenuItem[] | undefined;

 

  ngOnInit() {
      this.items = [{ label: 'Email Management'}];
      
  }


}
