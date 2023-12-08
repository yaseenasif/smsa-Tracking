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
                        Please find below TMS International Air Pre-Alert:
                    </h2>
                </td>
            </tr>
            <tr>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Processed Date
                </td>
                <td style="text-align: center;" colspan="3">
                    ${field1}
                </td>
            </tr>
            <tr>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Reference Number
                </td>
                <td  style="text-align: center;" colspan="3">
                    ${field2}
                </td>
            </tr>
            <tr>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Origin Country
                </td>
                <td style="text-align: center;" >
                    ${field3}
                </td>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Destination Country
                </td>
                <td style="text-align: center;" >
                    ${field4}
                </td>
            </tr>
            <tr>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Origin HUB/Gateway
                </td>
                <td style="text-align: center;" >
                    ${field5}
                </td>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Destination HUB/Gateway
                </td>
                <td style="text-align: center;" >
                    ${field6}
                </td>
            </tr>
            <tr>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Total No of Shipments
                </td>
                <td style="text-align: center;" >
                    ${field7}
                </td>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Carrier
                </td>
                <td style="text-align: center;" >
                    ${field8}
                </td>
            </tr>
            <tr>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Airline AWB No
                </td>
                <td style="text-align: center;" >
                    ${field9}
                </td>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Flight No
                </td>
                <td style="text-align: center;" >
                    ${field10}
                </td>
            </tr>
            <tr>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    No of bags
                </td>
                <td style="text-align: center;" >
                    ${field11}
                </td>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Pallets
                </td>
                <td style="text-align: center;" >
                    ${field12}
                </td>
            </tr>
            <tr>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Weight (kg)
                </td>
                <td style="text-align: center;" >
                    ${field13}
                </td>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Mode
                </td>
                <td style="text-align: center;" >
                    ${field14}
                </td>
            </tr>
            <tr>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Local Departure Date
                </td>
                <td style="text-align: center;" >
                    ${field15}
                </td>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Local Arrival Date
                </td>
                <td style="text-align: center;" >
                    ${field16}
                </td>
            </tr>
            <tr>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Local Departure Time
                </td>
                <td style="text-align: center;" >
                    ${field17}
                </td>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Local Arrival Time
                </td>
                <td style="text-align: center;" >
                    ${field18}
                </td>
            </tr>
            <tr>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Remarks
                </td>
                <td style="text-align: center;" colspan="3">
                    ${field19}
                </td>
            </tr>
        </tbody>
    </table>
</body>
</html>