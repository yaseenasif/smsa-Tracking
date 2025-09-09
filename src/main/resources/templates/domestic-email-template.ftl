<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>email</title>
    <style>
        #customers {
  font-family: Arial, Helvetica, sans-serif;
  border-collapse: collapse;
  width: 100%;
}

#customers td, #customers th {
  border: 1px solid #ddd;
  padding: 8px;
}

#customers tr:nth-child(even){background-color: #f2f2f2;}
    </style>
</head>
<body>
    <table id="customers" style="width: 100%;">
        <tbody>
            <tr>
                <td  colspan="4" style="padding-bottom: 1rem;">
                    <h2 style="text-align: center;">
                        Please find below TMS Domestic Pre-Alert:
                    </h2>
                </td>
            </tr>
            <tr>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Processed Date
                </td>
                <td style="text-align: center;" colspan="3">
                    ${createdAt}
                </td>
            </tr>
            <tr>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Reference Number
                </td>
                <td  style="text-align: center;" colspan="3">
                    ${referenceNumber}
                </td>
            </tr>
            <tr>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Origin HUB/Station
                </td>
                <td style="text-align: center;" >
                    ${originLocation}
                </td>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Destination HUB/Station
                </td>
                <td style="text-align: center;" >
                    ${destinationLocation}
                </td>
            </tr>
            <tr>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Total No of Shipments
                </td>
                <td style="text-align: center;" >
                    ${numberOfShipments}
                </td>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Carrier
                </td>
                <td style="text-align: center;" >
                    ${vehicleType}
                </td>
            </tr>
            <tr>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    No of bags
                </td>
                <td style="text-align: center;" >
                    ${numberOfBags}
                </td>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Pallets
                </td>
                <td style="text-align: center;" >
                    ${numberOfPallets}
                </td>
            </tr>
            <tr>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Mode
                </td>
                <td style="text-align: center;" >
                    ${transportMode}
                </td>
            </tr>
            <tr>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Route Number
                </td>
                <td style="text-align: center;" colspan="3">
                    ${routeNumber}
                </td>
            </tr>
             <tr>
                            <td style="background-color:#420097;color:white;font-weight: bolder;">
                                Security Tag
                            </td>
                            <td style="text-align: center;" colspan="3">
                                ${securityTag}
                            </td>
                        </tr>
              <tr>
                             <td style="background-color:#420097;color:white;font-weight: bolder;">
                                 Vehicle Number
                             </td>
                             <td style="text-align: center;" colspan="3">
                                 ${vehicleNumber}
                             </td>
                         </tr>
            <tr>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Remarks
                </td>
                <td style="text-align: center;" colspan="3">
                    ${remarks}
                </td>
            </tr>
        </tbody>
    </table>
</body>
</html>