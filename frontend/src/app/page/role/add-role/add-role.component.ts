import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-add-role',
  templateUrl: './add-role.component.html',
  styleUrls: ['./add-role.component.scss']
})
export class AddRoleComponent {
  items: MenuItem[] | undefined;

  permission!: Permission[];
  selectedPermission!: Permission[];

  constructor() { }
  name!:string;
  
  ngOnInit(): void {
    this.items = [{ label: 'Role list',routerLink:'/role'},{ label: 'Add Role'}];

    this.permission = [
      {name: 'All', id:1},
      {name: 'Head', id:2},
      {name: 'Admin', id:3},
      {name: 'Admin', id:4},
      {name: 'Admin', id:5}
  ];
  }
}

interface Permission {
  name: string,
  id: number
}