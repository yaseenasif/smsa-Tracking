import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-location-port-list',
  templateUrl: './location-port-list.component.html',
  styleUrls: ['./location-port-list.component.scss']
})
export class LocationPortListComponent implements OnInit {

  constructor() { }
  products:any=[{portName:"Demo",location:"Demo"},
  {portName:"Demo",location:"Demo"},
  {portName:"Demo",location:"Demo"},
  {portName:"Demo",location:"Demo"},
  {portName:"Demo",location:"Demo"},
  {portName:"Demo",location:"Demo"},
  {portName:"Demo",location:"Demo"},
  {portName:"Demo",location:"Demo"},
  {portName:"Demo",location:"Demo"},
  {portName:"Demo",location:"Demo"},];
  items: MenuItem[] | undefined;

 

  ngOnInit() {
      this.items = [{ label: 'Location Port List'}];
  }



}
