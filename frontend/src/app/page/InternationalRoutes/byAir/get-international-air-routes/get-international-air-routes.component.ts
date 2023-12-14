import { MenuItem, MessageService } from 'primeng/api';
import { InternationalRouteService } from '../../service/international-route.service';
import { Component } from '@angular/core';
import { Routes } from '../../../../model/ShipmentRoutes';
import { InternationalRoutes } from 'src/app/model/InternationalRoute';

@Component({
  selector: 'app-get-international-air-routes',
  templateUrl: './get-international-air-routes.component.html',
  styleUrls: ['./get-international-air-routes.component.scss'],
  providers: [MessageService]
})
export class GetInternationalAirRoutesComponent {

  constructor(private internationalRouteService: InternationalRouteService, private messageService: MessageService) { }

  items: MenuItem[] | undefined;
  internationalRoutes!: InternationalRoutes[];
  visible: boolean = false;
  rID!: number
  first: number = 0;
  rows: number = 10;

  ngOnInit() {
    this.items = [{ label: 'International Route List For Air' }];
    this.getAllInternationalRoutes();
  }

  onPageChange(event: any) {

    this.first = event.first;
    this.rows = event.rows;
  }

  getAllInternationalRoutes() {
    this.internationalRouteService.getAllInternationalRoutesForAir().subscribe((res: InternationalRoutes[]) => {

      this.internationalRoutes = res;
    }, error => {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
  }

  deleteInternationalRouteByID(id: number) {
    
    this.internationalRouteService.deleteInternationalRoute(id).subscribe((res: any) => {
      
      this.visible = false;
      this.getAllInternationalRoutes();
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Route is deleted' });
    
    },(error:any)=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    });
  }

  showDialog(id: number) {
    this.rID = id;
    this.visible = true;
  }

}
interface PageEvent {
  first: number;
  rows: number;
  page: number;
  pageCount: number;
}
