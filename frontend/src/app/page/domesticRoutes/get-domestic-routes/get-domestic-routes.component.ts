import { Component } from '@angular/core';
import { DomesticRoutesService } from '../service/domestic-routes.service';
import { MenuItem, MessageService } from 'primeng/api';
import { Routes } from '../../../model/ShipmentRoutes';

@Component({
  selector: 'app-get-domestic-routes',
  templateUrl: './get-domestic-routes.component.html',
  styleUrls: ['./get-domestic-routes.component.scss'],
  providers: [MessageService]
})
export class GetDomesticRoutesComponent {

  constructor(private domesticRouteService: DomesticRoutesService, private messageService: MessageService) { }

  items: MenuItem[] | undefined;
  domesticRoutes!: Routes[];
  visible: boolean = false;
  rID!: number
  first: number = 0;
  rows: number = 10;

  ngOnInit() {
    this.items = [{ label: 'Domestic Route List' }];
    this.getAllDomesticRoutes();
  }

  onPageChange(event: any) {
    this.first = event.first;
    this.rows = event.rows;
}

  getAllDomesticRoutes() {
    this.domesticRouteService.getAllDomesticRoutes().subscribe((res: Routes[]) => {
      this.domesticRoutes = res;
    }, error => {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
  }

  deleteDomesticRouteByID(id: number) {
    this.domesticRouteService.deleteDomesticRoute(id).subscribe((res: Error) => {
      this.visible = false;
      debugger
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Route is deleted on id ' + res.message });
      debugger
      this.getAllDomesticRoutes();
    }, error => {
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
