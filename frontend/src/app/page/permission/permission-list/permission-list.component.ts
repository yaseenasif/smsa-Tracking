import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-permission-list',
  templateUrl: './permission-list.component.html',
  styleUrls: ['./permission-list.component.scss']
})
export class PermissionListComponent {
  constructor() { }
  products:any=[{name:"Demo"},
  {name:"Demo"},
  {name:"Demo"},
  {name:"Demo"},
  {name:"Demo"},
  {name:"Demo"},
  {name:"Demo"},
  {name:"Demo"},
  {name:"Demo"},
  {name:"Demo"},];
  items: MenuItem[] | undefined;

 

  ngOnInit() {
      this.items = [{ label: 'Permission'}];
  }

}
