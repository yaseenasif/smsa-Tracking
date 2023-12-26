import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-report-tiles',
  templateUrl: './report-tiles.component.html',
  styleUrls: ['./report-tiles.component.scss']
})
export class ReportTilesComponent {
  items: MenuItem[] | undefined;

 

  ngOnInit() {
      this.items = [{ label: 'Reports'}];
  }
}
