import { Component } from '@angular/core';
import {MenuItem} from 'primeng/api'

@Component({
  selector: 'app-edit-permission',
  templateUrl: './edit-permission.component.html',
  styleUrls: ['./edit-permission.component.scss']
})
export class EditPermissionComponent {
  items: MenuItem[] | undefined;

  constructor() { }
  name!:string;
  
  ngOnInit(): void {
    this.items = [{ label: 'Permission list',routerLink:'/permission'},{ label: 'Edit Permission'}];
  }
}
