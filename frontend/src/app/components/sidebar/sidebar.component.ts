import { Component, OnInit } from '@angular/core';


@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {

  constructor() { }

  configTurner:boolean=false;

  ngOnInit(): void {
  }

  turner(){
    this.configTurner=!this.configTurner;
  }
}
