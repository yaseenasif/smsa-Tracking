import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-role-list',
  templateUrl: './role-list.component.html',
  styleUrls: ['./role-list.component.scss']
})
export class RoleListComponent {
  constructor() { }
  products:any=[{name:"Demo",permission:[{id:1,name:"Demo"}]},
  {name:"Demo",permission:[{id:1,name:"Demo"},{id:1,name:"Demo"}]},
  {name:"Demo",permission:[{id:1,name:"Demo"}]},
  {name:"Demo",permission:[{id:1,name:"Demo"}]},
  {name:"Demo",permission:[{id:1,name:"Demo"},{id:1,name:"Demo"}]},
  {name:"Demo",permission:[{id:1,name:"Demo"}]},
  {name:"Demo",permission:[{id:1,name:"Demo"},{id:1,name:"Demo"},{id:1,name:"Demo"},{id:1,name:"Demo"}]},
  {name:"Demo",permission:[{id:1,name:"Demo"}]},
  {name:"Demo",permission:[{id:1,name:"Demo"},{id:1,name:"Demo"},{id:1,name:"Demo"}]},
  {name:"Demo",permission:[{id:1,name:"Demo"}]},];
  items: MenuItem[] | undefined;

 

  ngOnInit() {
      this.items = [{ label: 'Role List'}];
  }
}
