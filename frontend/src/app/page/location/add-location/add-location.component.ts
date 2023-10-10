import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-add-location',
  templateUrl: './add-location.component.html',
  styleUrls: ['./add-location.component.scss']
})
export class AddLocationComponent implements OnInit {

  items: MenuItem[] | undefined;

  constructor() { }
  name!:string;
  
  ngOnInit(): void {
    this.items = [{ label: 'Location List',routerLink:'/location'},{ label: 'Add Location'}];
  }

}
