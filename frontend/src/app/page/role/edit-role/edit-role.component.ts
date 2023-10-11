import { Component } from '@angular/core';
import {MenuItem} from 'primeng/api'

@Component({
  selector: 'app-edit-role',
  templateUrl: './edit-role.component.html',
  styleUrls: ['./edit-role.component.scss']
})
export class EditRoleComponent {
  items: MenuItem[] | undefined;

  permission!: Permission[];
  selectedPermission!: Permission[];

  constructor() { }
  name!:string;
  
  ngOnInit(): void {
    this.items = [{ label: 'Role list',routerLink:'/role'},{ label: 'Edit Role'}];

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
