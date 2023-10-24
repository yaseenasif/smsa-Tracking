import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { ShipmentStatus } from 'src/app/model/ShipmentStatus';
import { ShipmentStatusService } from '../service/shipment-status.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-update-shipment-status',
  templateUrl: './update-shipment-status.component.html',
  styleUrls: ['./update-shipment-status.component.scss'],
  providers:[MessageService]
})
export class UpdateShipmentStatusComponent {

  items: MenuItem[] | undefined;
  sSId!: number;
  name!:string;
  shipmentStatus:ShipmentStatus={
    id: null,
    name: null
  }

  constructor(private route: ActivatedRoute,
              private shipmentStatusService:ShipmentStatusService,
              private messageService: MessageService,
              private router: Router) { }

  ngOnInit(): void {
    this.sSId = +this.route.snapshot.paramMap.get('id')!;
    this.items = [{ label: 'Shipment Status list',routerLink:'/shipment-status'},{ label: 'Edit Shipment Status'}];
    this.getShipmentStatusById();
  }

  getShipmentStatusById(){
   this.shipmentStatusService.getShipmentStatusByID(this.sSId).subscribe((res:ShipmentStatus)=>{
    this.shipmentStatus.name=res.name;
   },error=>{
    this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Can not get shipment status by id'});
   })
  }
  
  onSubmit() {
    this.shipmentStatusService.updateShipmentStatusById(this.sSId,this.shipmentStatus).subscribe(res=>{
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Shipment Status is updated on id'+res.id});
      setTimeout(() => {
        this.router.navigate(['/shipment-status']);
      },800);
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Shipment Status is not updated'});
    })  
  }
}
