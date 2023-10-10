import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-add-driver',
  templateUrl: './add-driver.component.html',
  styleUrls: ['./add-driver.component.scss']
})
export class AddDriverComponent implements OnInit {
  items: MenuItem[] | undefined;

  constructor() { }
  name!:string;
  contactNumber!:string;
  referenceNumber!:string;
  ngOnInit(): void {
    this.items = [{ label: 'Driver List',routerLink:'/driver'},{ label: 'Add Driver'}];
  }

}
