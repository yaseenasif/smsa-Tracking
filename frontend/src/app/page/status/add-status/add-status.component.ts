import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-add-status',
  templateUrl: './add-status.component.html',
  styleUrls: ['./add-status.component.scss']
})
export class AddStatusComponent implements OnInit {

  items: MenuItem[] | undefined;

  constructor() { }
  name!:string;
  
  ngOnInit(): void {
    this.items = [{ label: 'Status List',routerLink:'/status'},{ label: 'Add Status'}];
  }

}
