/******************************************************************************
 *
 * Jacksum version 1.7.0 - checksum utility in Java
 * Copyright (C) 2001-2006 Dipl.-Inf. (FH) Johann Nepomuk Loefflmann,
 * All Rights Reserved, http://www.jonelo.de
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * E-mail: jonelo@jonelo.de
 *
 *****************************************************************************/

package org.mmocore.commons.crypt.algorithm;

public class Elf extends AbstractChecksum {

    protected long ghash;

    public Elf() {
        super();
        reset();
    }

    @Override
    public void reset() {
        value = 0;
        length = 0;
    }

    @Override
    public void update(final byte b) {
        value = (value << 4) + (b & 0xFF);
        final long ghash = value & 0xF0000000L;
        if (ghash != 0) {
            value ^= (ghash >>> 24);
        }
        value &= ~ghash;
        length++;
    }

    @Override
    public void update(final int b) {
        update((byte) (b & 0xFF));
    }

    @Override
    public byte[] getByteArray() {
        final long val = getValue();
        return new byte[]{(byte) ((val >> 24) & 0xff), (byte) ((val >> 16) & 0xff), (byte) ((val >> 8) & 0xff), (byte) (val & 0xff)};
    }


}
