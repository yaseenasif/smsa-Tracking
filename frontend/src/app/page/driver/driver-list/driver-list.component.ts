import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-driver-list',
  templateUrl: './driver-list.component.html',
  styleUrls: ['./driver-list.component.scss']
})
export class DriverListComponent implements OnInit {

  constructor() { }
  products:any=[{name:"Demo",contactNumber:"Demo",referenceNumber:"Demo"},
  {name:"Demo",contactNumber:"Demo",referenceNumber:"Demo"},
  {name:"Demo",contactNumber:"Demo",referenceNumber:"Demo"},
  {name:"Demo",contactNumber:"Demo",referenceNumber:"Demo"},
  {name:"Demo",contactNumber:"Demo",referenceNumber:"Demo"},
  {name:"Demo",contactNumber:"Demo",referenceNumber:"Demo"},
  {name:"Demo",contactNumber:"Demo",referenceNumber:"Demo"},
  {name:"Demo",contactNumber:"Demo",referenceNumber:"Demo"},
  {name:"Demo",contactNumber:"Demo",referenceNumber:"Demo"},
  {name:"Demo",contactNumber:"Demo",referenceNumber:"Demo"},];
  items: MenuItem[] | undefined;

 

  ngOnInit() {
      this.items = [{ label: 'Driver List'}];
  }

}
