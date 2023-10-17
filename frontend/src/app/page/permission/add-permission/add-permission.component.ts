import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-add-permission',
  templateUrl: './add-permission.component.html',
  styleUrls: ['./add-permission.component.scss']
})
export class AddPermissionComponent {
  items: MenuItem[] | undefined;

  constructor() { }
  name!:string;
  
  ngOnInit(): void {
    this.items = [{ label: 'Permission list',routerLink:'/permission'},{ label: 'Add Permission'}];
  }
}
