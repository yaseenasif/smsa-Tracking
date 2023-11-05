import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { AuthguardService } from 'src/app/auth-service/authguard/authguard.service';

@Component({
  selector: 'app-tile',
  templateUrl: './tile.component.html',
  styleUrls: ['./tile.component.scss']
})
export class TileComponent {

  constructor(private authguardService:AuthguardService) { }
  items: MenuItem[] | undefined;

 

  ngOnInit() {
      this.items = [{ label: 'International Shipment'}];
  }

  hasPermission(permission:string):boolean{
    return this.authguardService.hasPermission(permission)
  }
}
