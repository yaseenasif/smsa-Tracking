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
    debugger
    this.first = event.first;
    this.rows = event.rows;
}

  getAllDomesticRoutes() {
    this.domesticRouteService.getAllDomesticRoutes().subscribe((res: Routes[]) => {
      this.domesticRoutes = res;
    }, error => {
    })
  }

  deleteDomesticRouteByID(id: number) {
    this.domesticRouteService.deleteDomesticRoute(id).subscribe((res: Routes) => {
      this.visible = false;
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Route is deleted on id ' + res!.id!.toString() });
      this.getAllDomesticRoutes();
    }, error => {

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
